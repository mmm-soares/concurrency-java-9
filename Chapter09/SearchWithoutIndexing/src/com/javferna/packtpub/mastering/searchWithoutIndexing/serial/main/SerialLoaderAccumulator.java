package com.javferna.packtpub.mastering.searchWithoutIndexing.serial.main;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.BiConsumer;

import com.javferna.packtpub.mastering.searchWithoutIndexing.data.Product;
import com.javferna.packtpub.mastering.searchWithoutIndexing.data.ProductLoader;

public class SerialLoaderAccumulator implements
		BiConsumer<ArrayList<Product>, Path> {

	@Override
	public void accept(ArrayList<Product> list, Path path) {

		Product product=ProductLoader.load(path);
		list.add(product);
		
	}

}
