package com.epam.msvehicletracking.util;

import com.epam.msvehicletracking.model.Coordinate;

public interface DistanceCalculator {
    double calculateDistance(Coordinate startingPoint, Coordinate currentPoint);
}
