package com.viartemev.requestmapper.annotations;

import com.viartemev.requestmapper.RequestMappingItem;

import java.util.List;

public interface MappingAnnotation {

    List<RequestMappingItem> values();

}
