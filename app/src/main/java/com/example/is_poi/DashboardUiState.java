package com.example.is_poi;

import java.util.List;

public class DashboardUiState {
    public final List<Comuni> comuni;
    public final List<Alberghi> alberghi;

    public DashboardUiState(List<Comuni> comuni, List<Alberghi> alberghi) {
        this.comuni = comuni;
        this.alberghi = alberghi;
    }
}
