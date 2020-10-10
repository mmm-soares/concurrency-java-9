package com.javferna.packtpub.mastering.searchWithoutIndexing.serial.main;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.javferna.packtpub.mastering.searchWithoutIndexing.data.Product;
import com.javferna.packtpub.mastering.searchWithoutIndexing.data.ProductRecommendation;
import com.javferna.packtpub.mastering.searchWithoutIndexing.data.ProductReview;

public class SerialMainRecomendation {

	public static void main(String[] args) {
		String user = "A2JOYUS36FLG4Z";
		Path file = Paths.get("data");
		try {
			Date start, end;
			
			start=new Date();
			List<Product> productList = Files
					.walk(file, FileVisitOption.FOLLOW_LINKS)
					.filter(f -> f.toString().endsWith(".txt"))
					.collect(ArrayList<Product>::new,
							new SerialLoaderAccumulator(),
							List::addAll);
			
			Map<String, List<ProductReview>> productsByBuyer=productList
					.stream()
					.<ProductReview>flatMap(p -> p.getReviews().stream().map(r -> new ProductReview(p, r.getUser(), r.getValue())))
					.collect(Collectors.groupingBy( p -> p.getBuyer()));
			
			Map<String,List<ProductReview>> recommendedProducts=productsByBuyer.get(user)
					.stream()
					.map(p -> p.getReviews())
					.flatMap(Collection::stream)
					.map(r -> r.getUser())
					.distinct()
					.map(productsByBuyer::get)
					.flatMap(Collection::stream)
					.collect(Collectors.groupingBy(p -> p.getTitle()));
			

			List<ProductRecommendation> recommendations = recommendedProducts
					.entrySet()
					.stream()
					.map(entry -> new ProductRecommendation(
							entry.getKey(), 
							entry.getValue().stream().mapToInt(p -> p.getValue()).average().getAsDouble()))
					.sorted()
					.collect(Collectors.toList());
			
			end=new Date();
			
			recommendations.forEach(pr -> System.out.println(pr.getTitle()+": "+pr.getValue()));

			System.out.println(recommendations.size());
			System.out.println("Execution Time: "+(end.getTime()-start.getTime()));

		} catch (IOException e) {
			e.printStackTrace();
		}			
	}

}
