//package com.domain.search.service;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.domain.search.godaddy.api.GodaddyApiServiceCall;
//import com.domain.search.model.GetDomainStatusResponse;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@RestController
//public class FileWrite {
//
//	@Autowired
//	private GodaddyApiServiceCall bulkFetch;
//
//	@GetMapping("/get")
//	public String fileWriter() throws IOException {
////		creates file
////		File myObj = new File("/Users/arjunp/Desktop/domains.txt");
////	      if (myObj.createNewFile()) {
////	        System.out.println("File created: " + myObj.getName());
////	      } else {
////	        System.out.println("File already exists.");
////	      }
//		Map<Integer, String> availableDomains = new HashMap<>();
//		ObjectMapper objectMapper = new ObjectMapper();
//
//		try (FileWriter fw = new FileWriter("/Users/arjunp/Desktop/domains.txt", true);
//				BufferedWriter bw = new BufferedWriter(fw);
//				PrintWriter out = new PrintWriter(bw)) {
//			// available domains map
//
//			int totalCount = 0;
//			int availableCount = 0;
//
//			// prints pent A-Z
//			for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
//				for (char alphabet1 = 'A'; alphabet1 <= 'Z'; alphabet1++) {
//					String domain = new StringBuilder().append(alphabet).append(alphabet1).toString() + ".com";
//					GetDomainStatusResponse callGoDaddy = bulkFetch.getSingleDomainStatus(domain);
//					if (callGoDaddy.getAvailable()) {
//						availableDomains.put(availableCount, callGoDaddy.getDomain());
////						out.println(domain);
//						availableCount++;
//					}
//				}
//				totalCount++;
//			}
//			System.out.println("totalCount: " + totalCount + ", availableCount: " + availableCount);
//			return objectMapper.writeValueAsString(availableDomains);
//
//		} catch (IOException e) {
//			System.out.println("An error occurred.");
//			e.printStackTrace();
//		}
//		return objectMapper.writeValueAsString(availableDomains);
//	}
//}
//
////	int count1 = 0;
////	// prints pent A-Z
////	for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
////		for (char alphabet2 = 'A'; alphabet2 <= 'Z'; alphabet2++) {
////			for (char alphabet3 = 'A'; alphabet3 <= 'Z'; alphabet3++) {
////				for (char alphabet4 = 'A'; alphabet4 <= 'Z'; alphabet4++) {
////					for (char alphabet5 = 'A'; alphabet5 <= 'Z'; alphabet5++) {
////					String domain =	sb.append(alphabet).append(alphabet2).append(alphabet3)
////							.append(alphabet4).append(alphabet5).toString();
////							out.println(domain+".com");
////						count1++;
////					}
////				}
////			}
////		}
////	}
////	System.out.println(count1);
