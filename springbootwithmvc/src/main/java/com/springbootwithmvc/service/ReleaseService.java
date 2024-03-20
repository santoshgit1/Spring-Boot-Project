package com.springbootwithmvc.service;

import com.springbootwithmvc.exception.ReleaseDetailsNotFoundException;
import com.springbootwithmvc.model.ReleaseRequest;
import com.springbootwithmvc.model.ReleaseTO;

import java.util.List;

public interface ReleaseService {
    List<ReleaseTO> findAllReleases() throws ReleaseDetailsNotFoundException;

    ReleaseTO saveRelease(ReleaseRequest request) throws ReleaseDetailsNotFoundException;
}
