package com.javferna.packtpub.mastering.searchWithoutIndexing.serial.main;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import com.javferna.packtpub.mastering.searchWithoutIndexing.data.Product;

public class SerialMainSearch {

	public static void main(String args[]) {
		String query = "java";
		Path file = Paths.get("data");
		try {
			Date start, end;
			start=new Date();
			ArrayList<Product> results = Files
					.walk(file, FileVisitOption.FOLLOW_LINKS)
					.filter(f -> f.toString().endsWith(".txt"))
					.collect(ArrayList<Product>::new,
							new SerialObjectAccumulator(query),
							ArrayList::addAll);
			end=new Date();
			System.out.println("Resultados");
			System.out.println("*************");
			results.forEach(p -> System.out.println(p.getTitle()));
			System.out.println("Execution Time: "+(end.getTime()-start.getTime()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
