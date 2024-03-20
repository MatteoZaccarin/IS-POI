package com.example.is_poi;

import java.util.ArrayList;
import java.util.List;

public class DashboardUiState {
    public final List<Comuni> comuni;
    public final List<Alberghi> alberghi;

    public DashboardUiState(List<Comuni> comuni, List<Alberghi> alberghi) {
        this.comuni = comuni;
        this.alberghi = alberghi;
    }
    public List<String> ritornaStringheComuni(){
        List<String> comuni=new ArrayList<>();
        for (Comuni comune:this.comuni) {
            comuni.add(comune.Comune);
        }
        return comuni;
    }
}
