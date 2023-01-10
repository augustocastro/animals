import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

class Option {
	public int value;
	public String name;
	
	public Option(int value, String name) {
		this.value = value;
		this.name = name;
	}
}

public class Animals {
	
	@SuppressWarnings("serial")
	public static void main(String[] args) {
		Map<Option, Object> animais = new HashMap<>(){{
		    put(new Option(1, "vertebrado"), new HashMap<>() {{
		    	put(new Option(1, "ave"), new HashMap<>() {{
		    		put(new Option(1, "carnivoro"), new ArrayList<>() {{
		    			addAll(Arrays.asList("falcao", "aguia"));
		    		}});
		    		put(new Option(2, "ovivoro"), "pomba");
		    	}});
		    	put(new Option(2, "mamifero"), new HashMap<>() {{
		    		put(new Option(1, "ovivoro"), "homem");
		    		put(new Option(2, "herbivero"), "vaca");
		    	}});
		    }});
		    put(new Option(2, "invertebrado"), new HashMap<>() {{
		    	put(new Option(1, "inseto"), new HashMap<>() {{
		    		put(new Option(1, "hematafago"), "pulga");
		    		put(new Option(2, "herbivoro"), "largarta"); 
		    	}});
		    	put(new Option(2, "anelideo"), new HashMap<>() {{
		    		put(new Option(1, "hematafago"), "sanguessuga");
		    		put(new Option(2, "ovivoro"), "minhoca");
		    	}});
		    }});
		}};
		
		run(animais);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> run(Object object) {
		try (Scanner scanner = new Scanner(System.in)) {
			printOptions((Map<Option, Object>) object);
			
			System.out.println("Digite o número da opção: ");
			
			try {
				int option = scanner.nextInt();
				Object result = findByKey((Map<Option, Object>) object, option);
				
				if (result != null) {
					if (result instanceof Map) {
						return run(result);
					} else if (result instanceof List) {
						((List<?>) result).forEach(System.out::println);
					} else {
						System.out.println(result);
					}
				} else {
					System.out.println("Opção desconhecida!!!");
				}
			} catch (InputMismatchException e) {
				System.out.println("Entrada inválida, o valor deve ser um número inteiro.");
			}
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public  static Object findByKey(Map<Option, Object> map, int value) {
		Optional<Entry<Option, Object>> optional = map
				.entrySet().stream().filter(entry -> entry.getKey().value == value).findFirst();
		
		if (optional.isPresent()) {
			Object object = optional.get().getValue();
			
			try {
				return (Map<Option, Object>) (object);
			} catch (ClassCastException e) {
				return object; 
			}
		} 
		
		return null;
	}
	
	public static void printOptions(Map<Option, Object> map) {				
		Map<Option,Object> sorted = map.entrySet().stream()
				.sorted((e1, e2) -> Integer.compare(e1.getKey().value, e2.getKey().value))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(e1, e2) -> e1, LinkedHashMap::new));
		
		sorted.entrySet()
			.stream()
			.forEach(entry -> System.out.println(entry.getKey().value + " - " + entry.getKey().name));
	}
}
