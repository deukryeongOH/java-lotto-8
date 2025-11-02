package lotto;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.*;

import static camp.nextstep.edu.missionutils.Console.readLine;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        int price = inputPrice();
        int lotto_count = price / 1000;
        System.out.println("\n" + lotto_count + "개를 구매했습니다.");

        Lotto[] lottos = createLottos(lotto_count);

        List<Integer> correctNumbers = inputCorrectNumbers();
        int bonus = inputBonusNumber(correctNumbers);

        Map<Lotto.LottoRank, Integer> statistics = new EnumMap<>(Lotto.LottoRank.class);
        initMap(statistics);
        long totalPrize = getTotalPrize(correctNumbers, bonus, lottos, statistics);
        printDetails(statistics);
        double profitRate = Math.round(((double) totalPrize / price) * 100 * 10) / 10.0;
        System.out.printf("총 수익률은 %.1f%%입니다.\n", profitRate);

    }

    private static void printDetails(Map<Lotto.LottoRank, Integer> statistics) {
        System.out.println("\n당첨 통계");
        System.out.println("---");
        System.out.printf("%s - %d개\n", Lotto.LottoRank.FIFTH.getDescription(), statistics.get(Lotto.LottoRank.FIFTH));
        System.out.printf("%s - %d개\n", Lotto.LottoRank.FOURTH.getDescription(), statistics.get(Lotto.LottoRank.FOURTH));
        System.out.printf("%s - %d개\n", Lotto.LottoRank.THIRD.getDescription(), statistics.get(Lotto.LottoRank.THIRD));
        System.out.printf("%s - %d개\n", Lotto.LottoRank.SECOND.getDescription(), statistics.get(Lotto.LottoRank.SECOND));
        System.out.printf("%s - %d개\n", Lotto.LottoRank.FIRST.getDescription(), statistics.get(Lotto.LottoRank.FIRST));
    }

    private static long getTotalPrize(List<Integer> correctNumbers, int bonus, Lotto[] lottos, Map<Lotto.LottoRank, Integer> statistics) {
        long totalPrize = 0;
        for (Lotto lotto : lottos) {
            int matchCount = lotto.compareNumbers(correctNumbers);
            boolean bonusCheck = lotto.checkBonus(bonus);
            Lotto.LottoRank rank = Lotto.LottoRank.valueOf(matchCount, bonusCheck);

            if (rank != Lotto.LottoRank.NONE) {
                statistics.put(rank, statistics.get(rank) + 1);
                totalPrize += rank.getPrizeMoney();
            }
        }
        return totalPrize;
    }

    private static void initMap(Map<Lotto.LottoRank, Integer> statistics) {
        statistics.put(Lotto.LottoRank.FIFTH, 0);
        statistics.put(Lotto.LottoRank.FOURTH, 0);
        statistics.put(Lotto.LottoRank.THIRD, 0);
        statistics.put(Lotto.LottoRank.SECOND, 0);
        statistics.put(Lotto.LottoRank.FIRST, 0);
        statistics.put(Lotto.LottoRank.NONE, 0);
    }

    private static int inputBonusNumber(List<Integer> correctNumbers) {
        System.out.println("\n보너스 번호를 입력해 주세요.");
        int bonus = Integer.parseInt(readLine());
        validateNumberRange(bonus);
        if (correctNumbers.contains(bonus)) {
            throw new IllegalArgumentException("[ERROR] 보너스는 당첨 번호와 중복될 수 없습니다.");
        }
        return bonus;
    }

    private static Lotto[] createLottos(int lottoCount) {
        Lotto[] lottos = new Lotto[lottoCount];
        for (int i = 0; i < lottoCount; i++) {
            List<Integer> numbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);
            validateDuplication(numbers);
            lottos[i] = new Lotto(numbers);
            lottos[i].printNumbers();
        }
        return lottos;
    }

    private static List<Integer> inputCorrectNumbers() {
        System.out.println("\n당첨 번호를 입력해 주세요.");
        String[] correct_numbers = readLine().split(",");
        validateLength(correct_numbers.length);
        List<Integer> correctNumbers = new ArrayList<>();
        for (String s : correct_numbers) {
            validateNumberRange(Integer.parseInt(s));
            correctNumbers.add(Integer.parseInt(s));
        }
        validateDuplication(correctNumbers);
        return correctNumbers;
    }


    private static int inputPrice() {
        while (true) {
            try {
                return validateInputPrice();
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] 숫자 외의 값이 입력되었습니다.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static int validateInputPrice() {
        System.out.println("구입금액을 입력해 주세요.");
        int price = Integer.parseInt(readLine());
        if (price % 1000 != 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 1,000원 단위여야 합니다.");
        }
        return price;
    }

    private static void validateLength(int length) {
        try {
            if (length != 6) {
                throw new IllegalArgumentException("[ERROR] 당첨 번호는 6개여야 합니다.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException();
        }
    }

    private static void validateNumberRange(int number) {
        try {
            if (number < 1 || number > 45) {
                throw new IllegalArgumentException("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException();

        }
    }

    private static void validateDuplication(List<Integer> correctNumbers) {
        try {
            if (!checkDuplication(correctNumbers)) {
                throw new IllegalArgumentException("[ERROR] 로또 번호에 중복된 숫자가 있습니다.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException();
        }
    }
    private static boolean checkDuplication(List<Integer> correctNumbers) {
        Set<Integer> numbers = new HashSet<>(correctNumbers);
        return numbers.size() == correctNumbers.size();
    }


}
