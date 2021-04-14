package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Agent;
import com.lambdaschool.javaorders.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service(value = "agentServices")
public class AgentServicesImpl implements AgentServices{
    @Autowired
    private AgentRepository agentrepos;

    @Override
    public Agent findAgentById(long id) throws EntityNotFoundException {
        return agentrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent Id " + id + " Not Found"));
    }
}
