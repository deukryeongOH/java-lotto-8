package lotto;

import java.util.*;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
        if (!checkDuplication(numbers)) {
            throw new IllegalArgumentException("[ERROR] 로또 번호에 중복된 숫자가 있습니다.");
        }
    }

    // TODO: 추가 기능 구현

    private static boolean checkDuplication(List<Integer> correctNumbers) {
        Set<Integer> numbers = new HashSet<>(correctNumbers);
        return numbers.size() == correctNumbers.size();
    }

    public void printNumbers() {
        List<Integer> sortedNumbers = new ArrayList<>(this.numbers);
        Collections.sort(sortedNumbers);
        List<String> random = new ArrayList<>();
        for (int rand : sortedNumbers) {
            random.add(rand + "");
        }
        System.out.print("[");
        System.out.print(String.join(", ", random));
        System.out.println("]");
    }

}
