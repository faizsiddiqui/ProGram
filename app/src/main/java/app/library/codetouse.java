package app.library;

/**
 * Created by Chaitanya on 4/19/2015.
 */
public class codetouse {

    // maine yahan code likhke de dunga tum log use kar lena jaise bhi karna hai

    private int marks[];
    private String crop[]; // is array me mujhe sare crops chahie from the database
    private int length;

    private void quickSort(int lowerIndex, int higherIndex) {

        int i = lowerIndex + 1;
        int j = higherIndex;
        int pivot = marks[0];

        while (i <= j) {

            while (marks[i] < pivot) {
                i++;
            }
            while (marks[j] > pivot) {
                j--;
            }
            if (i <= j) {
                exchangeNumbers(i, j);
                i++;
                j--;
            }
        }

        exchangeNumbers(lowerIndex, j);

        if (lowerIndex < j)
            quickSort(lowerIndex, j - 1);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }

    private void exchangeNumbers(int i, int j) {
        int temp1 = marks[i];
        marks[i] = marks[j];
        marks[j] = temp1;
        String temp2 = crop[i];
        crop[i] = crop[j];
        crop[j] = temp2;
    }



    double a[]=new double[]{10,9.2,9,5,4.7,4.5,2,1.9,1.8,1.7,1.6};
    double values[]; //esme le aana sari values jo user enter kare and put 999 where value is not entered
    int i;


    private void marksnikalo() {


        double x,y,k,lowrange=0,highrange=0,m=0,lowestrange=0,highestrange=0;

        /*lowrange vo apni range jo hogi uski lower value hai aur highrange uski upper value
        m uski optimum value hai jahan apanko multiplier 1 chahie
        aur lowest aur highest vo values hai jahan apanko multiplier 0 karna hai
        to ye sari values le aana yahanpe*/

        for (i=0;i<11;i++) {
            if (values[i] != 999) {
                if (values[i] < lowrange && values[i] > highrange) {
                    if (values[i] < m) {
                        k = 1 / ((lowestrange - m) * (lowestrange - m));
                    } else {
                        k = 1 / ((highestrange - m) * (highestrange - m));
                    }

                } else {
                    if (values[i] < m) {
                        k = (0.87 - 1) / ((lowrange - m) * (lowrange - m));
                    } else {
                        k = (0.87 - 1) / ((highrange - m) * (highrange - m));
                    }
                }

                y = 1 - (((values[i] - m) * (values[i] - m)) / k);
                marks[i] += a[i] * y;
            }
        }
    }


    private void main(){

        //pehele sari values nikalna database se set karna jo variables die hai us hisab se fir eske aage ka kam karna
        marksnikalo();
        length=crop.length;
        quickSort(0,length-1);
       // eske bad khushi manao crop wali array show kar dena vahi final list hai jo chahie apanko
    }
}
