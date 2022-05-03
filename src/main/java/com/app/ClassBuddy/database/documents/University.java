package com.app.ClassBuddy.database.documents;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class University {
    
    private String name;
    private ArrayList<String> majorList;

    public University(String name) {
        this.name = name;
        majorList = new ArrayList<>();
        loadMajors();

    }

    private void loadMajors() {
        File majors = new File("ClassBuddy/src/main/resources/static/majors.txt");
        Scanner input = null;
        try {
            input = new Scanner(majors);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        while (input.hasNextLine()) {
            
            String line = input.nextLine();
            String reconstruct = "";
            Scanner n = new Scanner(line);
            while (n.hasNext()) {
                String hold = n.next().trim();
                if (hold.equals("B.A.") || hold.equals("BSE") || hold.equals("BSN") || hold.equals("(SOHE)") || hold.equals("B.S.") || hold.equals("BLA") || hold.equals("JBA") || hold.equals("JBS") || hold.equals("(L&S)") || hold.equals("(CALS)") || hold.equals("BBA")) {
                    reconstruct += hold + " ";

                } else {
                    if (hold.equals("AND") || hold.equals("FOR")) {
                        reconstruct += hold.toLowerCase() + " ";
                    } else {
                        reconstruct += hold.substring(0, 1) + hold.substring(1).toLowerCase() + " ";
                    }
                }
            
            }
            majorList.add(reconstruct);

        }
        

    }

    public ArrayList<String> getMajorList() {
        return this.majorList;
    }

    public void setMajorList(ArrayList<String> majorList) {
        this.majorList = majorList;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    


}
