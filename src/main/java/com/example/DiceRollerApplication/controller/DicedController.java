package com.example.DiceRollerApplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doomed-dice")
// public ModelAndView doomedDice() {
//     ModelAndView modelAndView = new ModelAndView("home");
//     return modelAndView;
// }
public class DicedController {

    @GetMapping("/total-combinations")
    public ResponseEntity<Integer> getTotalCombinations() {
        int numberOfDice = 2;
        int sides = 6;
        int total = (int) Math.pow(sides, numberOfDice);
        System.out.println("Total Combinations: " + total);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/distribution")
    public ResponseEntity<List<Map<String, Integer>>> getDistribution() {
        int sides = 6;
        List<Map<String, Integer>> distributionList = new ArrayList<>();
        for (int i = 1; i <= sides; i++) {
            for (int j = 1; j <= sides; j++) {
                int sum = i + j;
                Map<String, Integer> combinationMap = new HashMap<>();
                combinationMap.put("Die A", i);
                combinationMap.put("Die B", j);
                combinationMap.put("Sum", sum);
                distributionList.add(combinationMap);
                System.out.println("Combination Map: " + combinationMap);
            }
        }
        return ResponseEntity.ok(distributionList);
    }

    @GetMapping("/probability")
    public ResponseEntity<Map<Integer, String>> getProbability() {
        int sides = 6;
        int[] distribution = new int[11];
        for (int i = 1; i <= sides; i++) {
            for (int j = 1; j <= sides; j++) {
                int sum = i + j;
                distribution[sum - 2]++;
            }
        }
        Map<Integer, String> probabilityMap = new HashMap<>();
        for (int i = 0; i <= 10; i++) {
            probabilityMap.put(i + 2, distribution[i] + "/" + 36);
        }
        return ResponseEntity.ok(probabilityMap);
    }
    @ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
}