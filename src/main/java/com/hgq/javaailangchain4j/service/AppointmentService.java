package com.hgq.javaailangchain4j.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hgq.javaailangchain4j.entity.Appointment;
import org.springframework.stereotype.Service;

/**
 * AppointmentService
 *
 * @author hegq
 * @since 1.0.0
 */

@Service
public interface AppointmentService extends IService<Appointment> {
    Appointment getOne(Appointment appointment);
}
