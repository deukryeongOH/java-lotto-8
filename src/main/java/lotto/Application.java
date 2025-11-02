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
}
