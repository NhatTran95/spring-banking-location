package com.cg.service.locationRegion;

import com.cg.model.LocationRegion;
import com.cg.repository.ILocationRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LocationRegionServiceImpl implements ILocationRegionService {

    @Autowired
    private ILocationRegionRepository locationRegionRepository;


    @Override
    public List<LocationRegion> findAll() {
        return null;
    }

    @Override
    public Optional<LocationRegion> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public void create(LocationRegion locationRegion) {

    }

    @Override
    public void update(Long aLong, LocationRegion locationRegion) {

    }

    @Override
    public void removeById(Long aLong) {

    }
}
