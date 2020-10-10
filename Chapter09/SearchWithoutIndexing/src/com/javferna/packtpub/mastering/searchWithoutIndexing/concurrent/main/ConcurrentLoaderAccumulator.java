package com.javferna.packtpub.mastering.searchWithoutIndexing.concurrent.main;

import java.nio.file.Path;
import java.util.List;
import java.util.function.BiConsumer;

import com.javferna.packtpub.mastering.searchWithoutIndexing.data.Product;
import com.javferna.packtpub.mastering.searchWithoutIndexing.data.ProductLoader;

public class ConcurrentLoaderAccumulator implements
		BiConsumer<List<Product>, Path> {

	@Override
	public void accept(List<Product> list, Path path) {

		Product product=ProductLoader.load(path);
		list.add(product);
		
	}

}
