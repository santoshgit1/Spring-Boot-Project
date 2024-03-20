package com.springbootwithmvc.service;

import com.springbootwithmvc.model.ApplicationTO;
import com.springbootwithmvc.model.ApplicationRequest;

import java.util.List;

public interface ApplicationService {
    List<ApplicationTO> findAllApplication();

    ApplicationTO save(ApplicationRequest applicationRequest);
}
