package com.abdsoft.msbtestudyguide.Subject;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdsoft.msbtestudyguide.HomeActivity;
import com.abdsoft.msbtestudyguide.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubjectFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    View subView;

    private List<SubItem> subItems;

    String dept = new String();
    String subNames[];
    String subCodes[];

    HomeActivity activity;


    public SubjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        subView = inflater.inflate(R.layout.fragment_subject, container, false);

        activity = (HomeActivity)getActivity();

        activity.setTitle("Subjects");

        recyclerView = subView.findViewById(R.id.recyclerview_sub);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        AdView mAdView;

        mAdView = subView.findViewById(R.id.adView_sub);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        subItems = new ArrayList<>();



        String sem = activity.semesterSelected;

        switch (sem)
        {
            case "I SEM":

                subNames = new String[]{"English","Basic Physics","Basic Chemistry","Basic Maths"};
                subCodes = new String[]{"17101","17102","17103","17104"};
                break;

            case "II SEM":

                dept = activity.departmentSelected;
                switch (dept){
                    case "Auto. Engineering":
                        subNames = new String[]{"Communication Skills", "Applied Physics", "Applied Chemistry", "Engineering Mechanics", "Engineering Drawing", "Engineering Mathemactics"};
                        subCodes = new String[]{"17201","17202","17203","17204","17205","17216"};
                        break;
                    case "Civil Engineering":
			            subNames = new String[]{"Communication Skills", "Engineering Mechanics", "Applied Physics", "Applied Chemistry", "Construction Materials", "Engineering Mathematics"};
                        subCodes = new String[]{"17201","17204","17207","17208","17209","17216"};
                        break;
                    case "Computer Engineering":
                        subNames = new String[]{"Communication Skills", "Applied Physics", "Applied Chemistry", "Programming in 'C'", "Basic Electronics", "Engineering Mathematics"};
                        subCodes = new String[]{"17201","17210","17211","17212","17213","17216"};
                        break;
                    case "ENTC Engineering":
                        subNames = new String[]{"Communication Skills", "Applied Physics", "Applied Chemistry", "Elements of Electronics", "Engineering Mathematics"};
                        subCodes = new String[]{"17201","17210","17211","17215","17216"};
                        break;
                    case "Elect Engineering":
                        subNames = new String[]{"Communication Skills", "Engineering Mechanics", "Applied Physics", "Applied Chemistry", "Fundamentals of EE", "Engineering Mathematics"};
                        subCodes = new String[]{"17201","17204","17210","17211","17214","17216"};
                        break;
                    case "IT Engineering":
                        subNames = new String[]{"Communication Skills", "Applied Physics", "Applied Chemistry", "Programming in 'C'", "Basic Electronics", "Engineering Mathematics"};
                        subCodes = new String[]{"17201","17210","17211","17212","17213","17216"};
                        break;
                    case "Mechanical Engineering":
                        subNames = new String[]{"Communication Skills", "Applied Physics", "Applied Chemistry", "Engineering Mechanics","Engineering Drawing", "Engineering Mathematics"};
                        subCodes = new String[]{"17201","17202","17203","17204","17205","17216"};
                        break;
                }
                break;

            case "III SEM":

                dept = activity.departmentSelected;
                switch (dept){
                    case "Auto. Engineering":
                        subNames = new String[]{"Applied Mathematics", "Strength of Materials", "Mechanical Engineering Drawing", "Materials and Manufacturing Processes", "Vehicle Layout and Transmission System"};
                        subCodes = new String[]{"17301","17304","17305","17306","17307"};
                        break;
                    case "Civil Engineering":
                        subNames = new String[]{"Applied Mathematics", "Building Construction", "Building Drawing", "Surveying", "Mechanics of Structures"};
                        subCodes = new String[]{"17301","17308","17309","17310","17311"};
                        break;
                    case "Computer Engineering":
                        subNames = new String[]{"Applied Mathematics", "Data Structures using 'C'", "Electrical Technology", "Relational Database Management System", "Digital Techniques"};
                        subCodes = new String[]{"17301","17330","17331","17332","17333"};
                        break;
                    case "ENTC Engineering":
                        subNames = new String[]{"Applied Mathematics", "Electronic Instruments & Measurements", "Electrical Engineering", "Electronics Devices and Circuits", "Principles of  Digital Techniques"};
                        subCodes = new String[]{"17301","17317","17318","17319","17320"};
                        break;
                    case "Elect Engineering":
                        subNames = new String[]{"Applied Mathematics", "Basic Electronics(Electrical)", "Electrical & Electronic Measurement", "Electrical Circuits and Networks", "Electrical Power Generation"};
                        subCodes = new String[]{"17301","17321","17322","17323","17324"};
                        break;
                    case "IT Engineering":
                        subNames = new String[]{"Applied Mathematics", "Data Structures using 'C'", "Electrical Technology", "Relational Database Management System", "Digital Techniques"};
                        subCodes = new String[]{"17301","17330","17331","17332","17333"};
                        break;
                    case "Mechanical Engineering":
                        subNames = new String[]{"Applied Mathematics", "Basic Electronics and Mechanics", "Mechanical Engineering Materials", "Strength of Materials", "Mechanical Engineering Drawing"};
                        subCodes = new String[]{"17301","17302","17303","17304","17305"};
                        break;
                }
                break;

            case "IV SEM":

                dept = activity.departmentSelected;
                switch (dept){
                    case "Auto. Engineering":
                        subNames = new String[]{"Automobile Manufacturing Process", "Heat Power Engineering", "Automobile Engines", "Automobile Systems and Body Engineering", "Theory of Machines"};
                        subCodes = new String[]{"17403","17407","17408","17409","17412"};
                        break;
                    case "Civil Engineering":
                        subNames = new String[]{"Transportation Engineering", "Advanced Surveying", "Geo Technical Engineering", "Hydraulics", "Theory of Structures"};
                        subCodes = new String[]{"17418","17419","17420","17421","17422"};
                        break;
                    case "Computer Engineering":
                        subNames = new String[]{"Computer Hardware and Maintenance", "Computer Network", "Microprocessor and Programming", "Object Oriented Programming"};
                        subCodes = new String[]{"17428","17429","17431","17432"};
                        break;
                    case "ENTC Engineering":
                        subNames = new String[]{"Industrial Measurements", "Analog Communication", "Power Electronics", "Linear Integrated Circuits"};
                        subCodes = new String[]{"17434","17440","17444","17445"};
                        break;
                    case "Elect Engineering":
                        subNames = new String[]{"Industrial Instrumentation", "DC Machines & Transformers", "Industry Electrical Systems - I"};
                        subCodes = new String[]{"17414","17415","17416"};
                        break;
                    case "IT Engineering":
                        subNames = new String[]{"Computer Hardware and Maintenance", "Computer Network", "Microprocessor and Programming", "Object Oriented Programming"};
                        subCodes = new String[]{"17428","17429","17431","17432"};
                        break;
                    case "Mechanical Engineering":
                        subNames = new String[]{"Manufacturing Process", "Electrical Engineering", "Thermal Engineering", "Fluid Mechanics & Machinery", "Theory of Machines"};
                        subCodes = new String[]{"17402","17404","17410","17411","17412"};
                        break;
                }
                break;

            case "V SEM":

                dept = activity.departmentSelected;
                switch (dept){
                    case "Auto. Engineering":
                        subNames = new String[]{"Two Wheeler Technology", "Hydraulics and Pneumatics(AE)", "Advanced Automobile Engines", "Basic Electrical and Electronics", "Design of Automobile Components"};
                        subCodes = new String[]{"17521","17522","17523","17524","17525"};
                        break;
                    case "Civil Engineering":
                        subNames = new String[]{"Estimating and Costing", "Irrigation Engineering", "Public Health Engineering", "Concrete Technology", "Design of Steel Structures"};
                        subCodes = new String[]{"17501","17502","17503","17504","17505"};
                        break;
                    case "Computer Engineering":
                        subNames = new String[]{"Operating System", "Software Engineering", "Computer Security", "Java Programming"};
                        subCodes = new String[]{"17512","17513","17514","17515"};
                        break;
                    case "ENTC Engineering":
                        subNames = new String[]{"Computer Hardware and Networking", "Microcontroller", "Digital Communication", "Control System & PLC", "Audio Video Engineering"};
                        subCodes = new String[]{"17533","17534","17535","17536","17537"};
                        break;
                    case "Elect Engineering":
                        subNames = new String[]{"Energy Conservation and Audit", "Industry Electrical Systems - II", "Switchgear & Protection", "Microcontroller and Applications", "AC Machines"};
                        subCodes = new String[]{"17506","17507","17508","17509","17511"};
                        break;
                    case "IT Engineering":
                        subNames = new String[]{"Operating System", "Software Engineering", "Java Programming", "Information Security", "Communication Technology"};
                        subCodes = new String[]{"17512","17513","17515","17518","17519"};
                        break;
                    case "Mechanical Engineering":
                        subNames = new String[]{"Automobile Engineering", "Advanced Manufacturing Process", "Measurement and Control", "Power Engineering", "Metrology and Quality Control"};
                        subCodes = new String[]{"17526","17527","17528","17529","17530"};
                        break;
                }
                break;

            case "VI SEM":

                dept = activity.departmentSelected;
                switch (dept){
                    case "Auto. Engineering":
                        subNames = new String[]{"Transport Management", "Automotive Electrical and Electronic Systems", "Vehicle Systems Maintenance", "Autotronics", "Automobile Air Conditioning"};
                        subCodes = new String[]{"17616","17617","17618","17619","17620"};
                        break;
                    case "Civil Engineering":
                        subNames = new String[]{"Highway Engineering", "Contracts and Accounts", "Design of RCC Structures", "Solid Waste Management", "Plumbing Services"};
                        subCodes = new String[]{"17602","17603","17604","17605","17607"};
                        break;
                    case "Computer Engineering":
                        subNames = new String[]{"Software Testing", "Embedded System", "Advanced Microprocessor"};
                        subCodes = new String[]{"17624","17626","17627"};
                        break;
                    case "ENTC Engineering":
                        subNames = new String[]{"Advanced Communication Systems", "Mobile Communication", "Embedded Systems", "Very Large Scale Integration", "Mechatronics"};
                        subCodes = new String[]{"17656","17657","17658","17659","17660"};
                        break;
                    case "Elect Engineering":
                        subNames = new String[]{"Testing & Maintenance of Electrical Equipments", "Power Electronics", "Illumination Engineering", "Modern Electric Traction", "Elements of Industrial Automation"};
                        subCodes = new String[]{"17637","17638","17639","17640","17641"};
                        break;
                    case "IT Engineering":
                        subNames = new String[]{"Object Oriented Modelling and Design", "Mobile Computing", "Fiber Optic Communication"};
                        subCodes = new String[]{"17630","17632","17633"};
                        break;
                    case "Mechanical Engineering":
                        subNames = new String[]{"Industrial Fluid Power", "Production Engineering & Robotics", "Design of Machine Elements", "Renewable Energy Sources & Management", "Refrigeration & Air Conditioning"};
                        subCodes = new String[]{"17608","17609","17610","17611","17612"};
                        break;
                }
                break;

            default:
                subNames = new String[]{""};
                subCodes = new String[]{""};
                break;
        }

        for (int iTmp = 0; iTmp < subNames.length; iTmp++)
        {
            SubItem subItem = new SubItem(subNames[iTmp], subCodes[iTmp]);
            subItems.add(subItem);
        }

        adapter = new SubAdapter(subItems, getActivity());
        recyclerView.setAdapter(adapter);




        return subView;
    }

    @Override
    public void onResume() {
        super.onResume();

        activity.setTitle("Subjects");
    }
}
