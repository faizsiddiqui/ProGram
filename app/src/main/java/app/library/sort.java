package app.library;

/**
 * Created by Chaitanya on 4/19/2015.
 */
public class sort {

    private double marks[];
    private String crop[]; // crop names from db
    private int length;

    private void quickSort(int lowerIndex, int higherIndex) {

        int i = lowerIndex + 1;
        int j = higherIndex;
        double pivot = marks[0];

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
        double temp1 = marks[i];
        marks[i] = marks[j];
        marks[j] = temp1;
        String temp2 = crop[i];
        crop[i] = crop[j];
        crop[j] = temp2;
    }


    double a[] = new double[]{10, 9.2, 9, 5, 4.7, 4.5, 2, 1.9, 1.8, 1.7, 1.6};
    double values[]; //all users values and put 999 where value is not entered
    int i;
    double x, y, k, lowrange[], highrange[], m[], lowestrange[], highestrange[], multiplierMin[], multiplierMax[];

    private void marksCalculator() {

        for (i = 0; i < 11; i++) {
            marks[i] = lowrange[i] = lowestrange[i] = m[i] = highrange[i] = highestrange[i] = 0;
        }

        /*lowrange vo apni range jo hogi uski lower value hai aur highrange uski upper value
        m uski optimum value hai jahan apanko multiplier 1 chahie
        aur lowest aur highest vo values hai jahan apanko multiplier 0 karna hai
        to ye sari values le aana yahanpe*/

        for (i = 0; i < 11; i++) {
            if (values[i] != 999) {
                if (values[i] < lowrange[i] && values[i] > highrange[i]) {
                    if (values[i] < m[i]) {
                        k = 1 / ((lowestrange[i] - m[i]) * (lowestrange[i] - m[i]));
                    } else {
                        k = 1 / ((highestrange[i] - m[i]) * (highestrange[i] - m[i]));
                    }

                } else {
                    if (values[i] < m[i]) {
                        k = (multiplierMin[i] - 1) / ((lowrange[i] - m[i]) * (lowrange[i] - m[i]));
                    } else {
                        k = (multiplierMax[i] - 1) / ((highrange[i] - m[i]) * (highrange[i] - m[i]));
                    }
                }

                y = 1 - (((values[i] - m[i]) * (values[i] - m[i])) / k);
                marks[i] += a[i] * y;
            }
        }
    }

    private void main() {
        marksCalculator();
        length = crop.length;
        quickSort(0, length - 1);
    }
}
