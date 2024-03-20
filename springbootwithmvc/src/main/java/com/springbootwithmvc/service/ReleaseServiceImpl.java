package com.springbootwithmvc.service;

import com.springbootwithmvc.entity.Release;
import com.springbootwithmvc.exception.ReleaseDetailsNotFoundException;
import com.springbootwithmvc.model.ReleaseRequest;
import com.springbootwithmvc.model.ReleaseTO;
import com.springbootwithmvc.repository.ReleaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReleaseServiceImpl implements ReleaseService{

    @Autowired
    private ReleaseRepository releaseRepository;

    @Override
    public List<ReleaseTO> findAllReleases() throws ReleaseDetailsNotFoundException {
        log.info("Inside the ReleaseServiceImpl.findAllReleases");
        List<Release> releases = releaseRepository.findAll();

        if(CollectionUtils.isEmpty(releases)){
            log.error("Release not found");
            throw new ReleaseDetailsNotFoundException("Release not found exception");
        }
        // apply the business logic
        List<ReleaseTO> releaseTOS = releases.stream().map(release ->  {
            ReleaseTO releaseTO = new ReleaseTO();
            releaseTO.setId(release.getId());
            releaseTO.setReleaseDate(release.getReleaseDate());
            releaseTO.setDescription(release.getDescription());
            return releaseTO;
        }).collect(Collectors.toList());

        log.info("End of ReleaseServiceImpl.findAllReleases");
        return releaseTOS;
    }

    @Override
    public ReleaseTO saveRelease(ReleaseRequest request) throws ReleaseDetailsNotFoundException {
        log.info("Inside the ReleaseServiceImpl.saveRelease,request;{} ", request);
        Release release = new Release();
        release.setReleaseDate(request.getReleaseDate());
        release.setDescription(request.getDescription());
        Release release1 = releaseRepository.save(release);

        if(Objects.isNull(release1)){
            log.error("Release details not saved");
            throw new ReleaseDetailsNotFoundException("Release details not saved");
        }

        ReleaseTO releaseTO = new ReleaseTO();
        releaseTO.setId(release1.getId());
        releaseTO.setReleaseDate(release1.getReleaseDate());
        releaseTO.setDescription(release1.getDescription());
        return releaseTO;
    }


}
