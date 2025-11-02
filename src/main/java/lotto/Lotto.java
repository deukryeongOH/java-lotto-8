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
    public enum LottoRank {
        FIRST(2_000_000_000L, "6개 일치 (2,000,000,000원)"),
        SECOND(30_000_000L, "5개 일치, 보너스 볼 일치 (30,000,000원)"),
        THIRD(1_500_000L, "5개 일치 (1,500,000원)"),
        FOURTH(50_000L, "4개 일치 (50,000원)"),
        FIFTH(5_000L, "3개 일치 (5,000원)"),
        NONE(0L, ""); // 꽝

        private final long prizeMoney;
        private final String description;

        LottoRank(long prizeMoney, String description) {
            this.prizeMoney = prizeMoney;
            this.description = description;
        }

        public long getPrizeMoney() {
            return prizeMoney;
        }

        public String getDescription() {
            return description;
        }

        // 일치 개수와 보너스 여부로 등수를 찾아주는 static 메소드
        public static LottoRank valueOf(int matchCount, boolean hasBonus) {
            if (matchCount == 6) {
                return FIRST;
            }
            if (matchCount == 5 && hasBonus) {
                return SECOND;
            }
            if (matchCount == 5) {
                return THIRD;
            }
            if (matchCount == 4) {
                return FOURTH;
            }
            if (matchCount == 3) {
                return FIFTH;
            }
            return NONE;
        }

    }

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

    public int compareNumbers(List<Integer> correctNumbers) {
        int cnt = 0;
        for (int number : correctNumbers) {
            if (numbers.contains(number)) {
                cnt++;
            }
        }
        return cnt;
    }

    public boolean checkBonus(int bonus) {
        return numbers.contains(bonus);
    }
}
