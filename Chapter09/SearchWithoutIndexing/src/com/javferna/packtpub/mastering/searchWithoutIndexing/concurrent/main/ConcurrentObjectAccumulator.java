package com.javferna.packtpub.mastering.searchWithoutIndexing.concurrent.main;

import java.nio.file.Path;
import java.util.List;
import java.util.function.BiConsumer;

import com.javferna.packtpub.mastering.searchWithoutIndexing.data.Product;
import com.javferna.packtpub.mastering.searchWithoutIndexing.data.ProductLoader;

public class ConcurrentObjectAccumulator implements
		BiConsumer<List<Product>, Path> {

	private String word;

	public ConcurrentObjectAccumulator(String word) {
		this.word = word;
	}

	@Override
	public void accept(List<Product> list, Path path) {

		Product product=ProductLoader.load(path);
		
		if (product.getTitle().toLowerCase().contains(word.toLowerCase())) {
			list.add(product);
		}
		
	}

}
