package ru.netology.Data;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CardData {

    private String cardId;

    public static CardData firstCard = new CardData("5559 0000 0000 0001");

    public static CardData secondCard = new CardData("5559 0000 0000 0002");
}
