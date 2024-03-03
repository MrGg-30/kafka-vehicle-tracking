package com.epam.msvehicletracking.util;

import com.epam.msvehicletracking.model.Coordinate;
import com.epam.msvehicletracking.util.DistanceCalculator;
import org.springframework.stereotype.Service;

@Service
public class EuclideanDistanceCalculator implements DistanceCalculator {

    @Override
    public double calculateDistance(Coordinate startingPoint, Coordinate currentPoint) {
        return Math.sqrt(Math.pow(startingPoint.latitude() - currentPoint.latitude(), 2)
                + Math.pow(startingPoint.longitude() - currentPoint.longitude(), 2));
    }
}
