package com.viartemev.requestmapper.annotations;

import com.viartemev.requestmapper.RequestMappingItem;

import java.util.Collections;
import java.util.List;

public class UnknownAnnotation implements MappingAnnotation {

    private static UnknownAnnotation unknownAnnotation = new UnknownAnnotation();

    private UnknownAnnotation() {

    }

    public static UnknownAnnotation getInstance() {
        return unknownAnnotation;
    }

    @Override
    public List<RequestMappingItem> values() {
        return Collections.emptyList();
    }

}
