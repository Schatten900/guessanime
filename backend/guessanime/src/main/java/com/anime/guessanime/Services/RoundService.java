package com.anime.guessanime.Services;

import com.anime.guessanime.Domains.Pair;
import com.anime.guessanime.Models.Character;
import com.anime.guessanime.Repositories.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class RoundService {

    @Autowired
    private CharacterRepository characterRepository;
    public RoundService(CharacterRepository characterRepository){
        this.characterRepository = characterRepository;
    }

    public List<List<Pair<Character, Boolean>>> getAllRounds(int rounds){
        //Logic to get all character and send to frontend

        List<List<Pair<Character,Boolean>>> roundsCharacterList = new ArrayList<>();
        List<Character> listCharacter = characterRepository.findAll();
        Collections.shuffle(listCharacter);

        //first lists all correct characters (rounds quantity)
        List<Pair<Character,Boolean>> tempCorrect = new ArrayList<>();
        for (int i=0; i < rounds; ++i){
            tempCorrect.add(new Pair<>(listCharacter.get(i),true));
            //System.out.println("Inserting character correct: " + tempCorrect.get(i).getL());
        }

        //second list all wrong characters (rounds*4) - rounds
        List<Pair<Character,Boolean>> tempWrong = new ArrayList<>();
        for (int i=rounds; i < rounds*4; ++i){
            tempWrong.add(new Pair<>(listCharacter.get(i),false));
            //System.out.println("Inserting character wrong: " + tempWrong.get(tempWrong.size() - 1).getL());
        }

        //Make a temp list which receives 3 wrong and 1 correct character
        for (int i=0; i < rounds; ++i){
            //System.out.println("Making rounds list");
            List<Pair<Character,Boolean>> actualRound = new ArrayList<>();
            Pair<Character,Boolean> rightChar = tempCorrect.get(i);
            actualRound.add(rightChar);
            //System.out.println(rightChar.getL());
            for (int j=i; j < i+3; ++j) {
                Pair<Character,Boolean> wrongChar = tempWrong.get(j);
                actualRound.add(wrongChar);
                //System.out.println(wrongChar.getL());
            }
            Collections.shuffle(actualRound);
            roundsCharacterList.add(actualRound);
        }

        //For debugging
        for (int i=0; i < rounds; ++i){
            System.out.println("Round - " + (i+1));
            for (int j=0; j < 4; ++j){
                System.out.println(roundsCharacterList.get(i).get(j).getL().getName() + " - "+
                        roundsCharacterList.get(i).get(j).getR().toString());
            }
        }

        System.out.println("Send successfully");
        return roundsCharacterList;
    }

}
