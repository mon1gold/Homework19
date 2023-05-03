import java.util.Arrays;

public class ValueCalculator {
    private double[] realNumbers;
    private int arraySize;
    private int arrayHalfSize;

    public ValueCalculator(int arraySize) {
        if (arraySize < 1_000_000) {
            arraySize = 1_000_000;
        }
        this.arraySize = arraySize;
        this.arrayHalfSize = arraySize / 2;
        this.realNumbers = new double[arraySize];
    }

    public int getArraySize() {
        return arraySize;
    }

    public int getArrayHalfSize() {
        return arrayHalfSize;
    }

    public void calculator() {
        long start = System.currentTimeMillis();
        Arrays.fill(realNumbers, 1);
        double[] array1 = Arrays.copyOfRange(realNumbers, 0, arrayHalfSize);
        double[] array2 = Arrays.copyOfRange(realNumbers, arrayHalfSize, arraySize);
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < array1.length; i++) {
                array1[i] = (float) (array1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int j = 0; j < array2.length; j++) {
                array2[j] = (float) (array2[j] * Math.sin(0.2f + j / 5) * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));
            }
        });

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(array1, 0, realNumbers, 0, arrayHalfSize);
        System.arraycopy(array2, 0, realNumbers, arrayHalfSize, arrayHalfSize);
        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start) + " ms");
    }
}
