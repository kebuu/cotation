package com.kebuu.builder.impl;

/**
 * La moyenne mobile à n séance, n étant > 0.
 * La moyenne mobile à 1 séance est les cours d'aujourd'hui, la moyenne mobile à 2 séances la moyenne du cours d'aujourd'hui et du cours d'hier.
 */
public class SimpleMobileMeanBuilder extends WeightedMobileMeanBuilder {

    private static final String MOBILE_MEAN_PREFIX_NAME = "mobile_mean_";

    public SimpleMobileMeanBuilder(int mobileMeanRange) {
        super(mobileMeanRange, MOBILE_MEAN_PREFIX_NAME);
    }

    @Override
    protected double getWeight(int i) {
        return 1.0;
    }
}