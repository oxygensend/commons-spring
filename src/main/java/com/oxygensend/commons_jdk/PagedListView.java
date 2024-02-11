package com.oxygensend.commons_jdk;

import java.util.List;

public record PagedListView<T>(List<T> data, int numberOfElements, int currentPage, int totalPages) {
}
