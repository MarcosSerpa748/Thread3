package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        ExecutorService executores = Executors.newFixedThreadPool(3);

        List<Double> valoresDeProdutos = List.of(10.0,250.0,100.0,145.0,32.0);
        List<Future<Double>> valoresDePordutosDescontados = new ArrayList<>();

        valoresDeProdutos.forEach(e ->{

            Future<Double> valorDescontado = executores.submit(() ->{

                Double resultadoPorcentagem = 10.0/100.0;
                Double valorDesconto = e * resultadoPorcentagem;
                Thread.sleep(500);

                return e - valorDesconto;
            });

          valoresDePordutosDescontados.add(valorDescontado);
        });

        valoresDePordutosDescontados.forEach(e ->{
            try {
                Double valorProcessado = e.get();
                System.out.println("Valor com 10% de desconto:"+valorProcessado);

            }catch (InterruptedException excecao){
                System.out.println("A Thread principal foi interrompida.");

            }catch (ExecutionException excecao){
                System.out.println("Erro na hora do processamento! Tipo de erro:"+excecao.getCause().getLocalizedMessage());
            }finally {
                executores.shutdown();
            }
        });

    }
}