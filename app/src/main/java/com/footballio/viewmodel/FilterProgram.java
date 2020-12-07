package com.footballio.viewmodel;

import com.footballio.model.login.dashboard.LibraryProgram;

import java.util.ArrayList;
import java.util.List;

public class FilterProgram {
    private static FilterProgram filterProgram = null;

    public List<LibraryProgram> performFiltering(int sparetime, List<LibraryProgram> programList) {
        List<LibraryProgram> filterList = new ArrayList<>();
        if (sparetime > 0) {
            LibraryProgram program = null;
            for (int i = 0; i < programList.size(); i++) {
                program = programList.get(i);
                String time = program.getTime();
                time = time.substring(0, time.length() - 1);
                if (Integer.parseInt(time) <= sparetime) {
                    filterList.add(program);
                }
            }
            program = new LibraryProgram();
            program.setData(filterList);

        }
        return filterList;
    }

    public static FilterProgram getInstance() {
        if (filterProgram == null) {
            filterProgram = new FilterProgram();
        }
        return filterProgram;
    }


}
