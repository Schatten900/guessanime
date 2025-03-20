package com.anime.guessanime.Services;

import com.anime.guessanime.Domains.Pair;
import com.anime.guessanime.Models.Character;
import com.anime.guessanime.Models.CharacterDTO;
import com.anime.guessanime.Repositories.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class RoundService {

    @Autowired
    private CharacterRepository characterRepository;
    public RoundService(CharacterRepository characterRepository){
        this.characterRepository = characterRepository;
    }

    public List<List<Pair<CharacterDTO, Boolean>>> getAllRounds(int rounds){
        //Logic to get all character and send to frontend

        List<List<Pair<CharacterDTO,Boolean>>> roundsCharacterList = new ArrayList<>();
        List<Character> listCharacter = characterRepository.findAll();
        Collections.shuffle(listCharacter);

        //first lists all correct characters (rounds quantity)
        List<Pair<CharacterDTO,Boolean>> tempCorrect = new ArrayList<>();
        for (int i=0; i < rounds; ++i){
            Character character = listCharacter.get(i);
            CharacterDTO characterDTO = new CharacterDTO(character);
            tempCorrect.add(new Pair<>(characterDTO, true));
        }

        //second list all wrong characters (rounds*4) - rounds
        List<Pair<CharacterDTO,Boolean>> tempWrong = new ArrayList<>();
        for (int i=rounds; i < rounds*4; ++i){
            Character character = listCharacter.get(i);
            CharacterDTO characterDTO = new CharacterDTO(character);
            tempWrong.add(new Pair<>(characterDTO, false));
        }

        //Make a temp list which receives 3 wrong and 1 correct character
        for (int i=0; i < rounds; ++i){
            List<Pair<CharacterDTO, Boolean>> actualRound = new ArrayList<>();
            Pair<CharacterDTO, Boolean> rightChar = tempCorrect.get(i);
            actualRound.add(rightChar);

            for (int j = i; j < i + 3; ++j) {
                Pair<CharacterDTO, Boolean> wrongChar = tempWrong.get(j);
                actualRound.add(wrongChar);
            }
            Collections.shuffle(actualRound);
            roundsCharacterList.add(actualRound);
        }

        System.out.println("Send successfully");
        return roundsCharacterList;
    }

    public String summarization(String text) throws IOException, InterruptedException {

        System.out.println("Texto recebido: " + text); // Log do texto recebido
        String basePath = System.getProperty("user.dir");
        String pythonScriptPath = Paths.get(basePath, "src/main/python_files/AIService.py").toString();
        String pythonExecutable = Paths.get(basePath, "src/main/python_files/venv/Scripts/python.exe").toString();

        ProcessBuilder pb = new ProcessBuilder(pythonExecutable, pythonScriptPath, text);
        pb.redirectErrorStream(true);

        Process process = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        String line;
        StringBuilder newText = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            newText.append(line);
        }

        StringBuilder errorText = new StringBuilder();
        while ((line = errorReader.readLine()) != null) {
            errorText.append(line);
        }

        process.waitFor(2, TimeUnit.MINUTES);

        if (errorText.length() > 0) {
            System.err.println("Python script failed: " + errorText.toString());
        }

        if (newText.toString().isEmpty()) {
            throw new IOException("Failed on summarization text");
        }
        System.out.println("Resumo gerado: " + newText.toString()); // Log do resumo gerado

        return newText.toString();
    }

}
