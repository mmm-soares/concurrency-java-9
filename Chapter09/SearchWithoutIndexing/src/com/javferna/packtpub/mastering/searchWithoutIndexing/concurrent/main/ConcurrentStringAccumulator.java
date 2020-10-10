package com.javferna.packtpub.mastering.searchWithoutIndexing.concurrent.main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.BiConsumer;

public class ConcurrentStringAccumulator implements BiConsumer<List<String>, Path> {

	private String word;

	public ConcurrentStringAccumulator(String word) {
		this.word = word.toLowerCase();
	}

	@Override
	public void accept(List<String> list, Path path) {
		long counter;
		try  {
			counter = Files
					.lines(path)
					.map(l -> l.split(":")[1].toLowerCase())
					.filter(l -> l.contains(word.toLowerCase()))
					.count();
			if (counter>0) {
				list.add(path.toString());
			}

		} catch (Exception e) {
			System.out.println(path);
			e.printStackTrace();
		}
	}

}
