package com.javferna.packtpub.mastering.searchWithoutIndexing.serial.main;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.BiConsumer;

import com.javferna.packtpub.mastering.searchWithoutIndexing.data.Product;
import com.javferna.packtpub.mastering.searchWithoutIndexing.data.ProductLoader;

public class SerialObjectAccumulator implements
		BiConsumer<ArrayList<Product>, Path> {

	private String word;

	public SerialObjectAccumulator(String word) {
		this.word = word;
	}

	@Override
	public void accept(ArrayList<Product> list, Path path) {

		Product product=ProductLoader.load(path);
		
		if (product.getTitle().toLowerCase().contains(word.toLowerCase())) {
			list.add(product);
		}
		
	}

}
