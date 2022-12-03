package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {

    @FXML
    private Button button;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private Label label;

    @FXML
    private TextField textField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void handleButtonAction(javafx.event.ActionEvent actionEvent) throws FileNotFoundException {
        lineChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
        series.getData().clear();
        PL p = new PL();
        p.massive();
        p.PL(Double.valueOf(textField.getText()));
        series.getData().clear();
        for (int i = 0; i < p.Xx.length; i++) {
            series.getData().add(new XYChart.Data<String, Number>(String.valueOf(p.Xx[i]), p.Yy[i]));
        }
        for (int i = 1; i < p.Xx.length-1; i++) {
            series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(p.Xx[i]), (p.Yy[i-1]+p.Yy[i]+p.Yy[i+1])/3));
        }
        lineChart.getData().addAll(series, series1);

        label.setText(p.PL(Double.valueOf(textField.getText())));
    }
}
class excel {
    String file = "Без имени 1.csv";
    ArrayList<String> XTime = new ArrayList<>();
    ArrayList<String> YValue = new ArrayList<>();
    int index = 0;


    String [] str;

    excel() throws FileNotFoundException {
    }
    void table() throws FileNotFoundException {
        FileReader reader = new FileReader(file);
        Scanner s = new Scanner(reader);
        while (s.hasNext()) {
            str = s.nextLine().split(",");
            XTime.add(str[0]);
            YValue.add(str[1]);
        }
        System.out.println(Arrays.toString(XTime.toArray()));
        System.out.println(Arrays.toString(YValue.toArray()));


    }
}
class PL extends excel{
    double [] Xx;
    double [] Yy;
    double sum;
    double fx;
    double Lpolinom;
    int N;
    String [] strXx;
    String [] strYy;
    String buffer;
    String spliter = " ";
    Scanner sc = new Scanner(System.in);

    PL() throws FileNotFoundException {
    }

    void massive() throws FileNotFoundException {
        table();
        Xx = new double[XTime.size()-1];
        Yy = new double[XTime.size()-1];
        for (int i = 1; i < XTime.size(); i++) {
            Xx[i-1] = Double.parseDouble(XTime.get(i));
            Yy[i-1] = Double.parseDouble(YValue.get(i));
        }
        System.out.println(Arrays.toString(Xx));
        System.out.println(Arrays.toString(Yy));
    }
    //    void stringer(int N) {
//        this.N = N;
//        strXx = new String[N];
//        strYy = new String[N];
//        Xx = new double[N];
//        Yy = new double[N];
//        buffer = sc.nextLine();
//        strXx = buffer.split(spliter);
//        buffer = sc.nextLine();
//        strYy = buffer.split(spliter);
//        if ((strXx.length == N) && (strYy.length == N)) {
//            for (int i = 0; i < Xx.length; i++) {
//                Xx[i] = Double.parseDouble(strXx[i]);
//            }
//            for (int i = 0; i < Yy.length; i++) {
//                Yy[i] = Double.parseDouble(strYy[i]);
//            }
//            System.out.println(Arrays.toString(Xx));
//            System.out.println(Arrays.toString(Yy));
//        }
//        else {
//            return;
//        }
//    }
    String PL(double fx) {
        Lpolinom = 1;
        this.fx = fx;
        for (int i = 0; i < Xx.length; i++) {
            for (int j = 0; j < Xx.length; j++) {
                if (!(i == j)) {
                    Lpolinom *= (fx - Xx[j])/(Xx[i]-Xx[j]);
                }
            }
            sum += Yy[i]*Lpolinom;
//            System.out.println("Итерация i = " + i + ",Lpolinom = " + Lpolinom + " sum = " + sum);
            Lpolinom = 1;
        }
        return ("График функции" +
                " в значении\n " + fx + " равен = " + sum/2);
//        System.out.println("Интерполляционный многочлен Лагранжа в значении " + fx + " равен = " + sum);
    }




}

