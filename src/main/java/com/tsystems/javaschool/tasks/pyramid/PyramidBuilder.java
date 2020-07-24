package com.tsystems.javaschool.tasks.pyramid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        try {
            if (inputNumbers.size() > Integer.MAX_VALUE / 2) {
                throw new CannotBuildPyramidException();
            }
            Collections.sort(inputNumbers);
            int[] inputNumberArr = new int[inputNumbers.size()];
            for (int i = 0; i < inputNumbers.size(); i++) {
                inputNumberArr[i] = inputNumbers.get(i);
            }

            int count = 1;
            int correctLength;
            int rowCount = 1;
            int columnCount = 1;
            int columnCountWith0 = 1;

            for (correctLength = 1; correctLength < inputNumberArr.length; correctLength += count) {
                count++;
                rowCount++;
                columnCount++;
                columnCountWith0 += 2;
            }
            if (correctLength != inputNumberArr.length) {
                throw new CannotBuildPyramidException();
            }
            int[][] rectangle = new int[rowCount][columnCount];
            int[][] resultArr = new int[rowCount][columnCountWith0];

            //rectangle filling
            int k = 0;
            for (int row = 0; row < rowCount; row++) {
                for (int column = 0; column < columnCount; column++) {
                    rectangle[row][column] = row < column ? 0 : inputNumberArr[k++];
                }
            }

            for (int i = 0; i < rectangle.length; i++) {
                resultArr[i] = add0(rectangle[i]);
            }
            return resultArr;
        } catch (Exception e) {
            throw new CannotBuildPyramidException();
        }
    }

    public int[] add0(int[] inputArr) {

        List<Integer> collect = Arrays.stream(inputArr)
                .boxed()
                .collect(Collectors.toList());

        int zeroCount = 0;
        for (Integer digit : collect) {
            if (digit == 0) {
                zeroCount++;
            }
        }
        List<Integer> listWith0 = new ArrayList<>();
        for (int i = 0; i < zeroCount; i++) {
            listWith0.add(0);
        }
        listWith0.addAll(collect);

        if (zeroCount != listWith0.size() - 1) {
            for (int i = 0; i < listWith0.size(); i++) {
                if (listWith0.get(i) != 0) {
                    listWith0.add(i + 1, 0);
                }
            }
        }
        listWith0.remove(listWith0.size() - 1);

        return listWith0.stream()
                .mapToInt(x -> x)
                .toArray();
    }
}
