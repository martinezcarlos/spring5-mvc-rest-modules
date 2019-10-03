package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;
import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by carlosmartinez on 2019-04-08 15:18
 */
@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {

  private final CategoryRepository categoryRepository;
  private final CustomerRepository customerRepository;
  private final VendorRepository vendorRepository;

  @Override
  public void run(final String... args) {
    loadCategories();
    loadCustomers();
    loadVendors();
  }

  private void loadCategories() {
    final List<Category> catgories = Arrays.asList(Category.builder().name("Fruits").build(),
        Category.builder().name("Dried").build(), Category.builder().name("Fresh").build(),
        Category.builder().name("Exotic").build(), Category.builder().name("Nuts").build());
    categoryRepository.saveAll(catgories);
    System.out.println("Categories loaded = " + categoryRepository.count());
  }

  private void loadCustomers() {
    final List<Customer> customers = Arrays.asList(
        Customer.builder().firstname("Michale").lastname("Weston").build(),
        Customer.builder().firstname("Sam").lastname("Axe").build());
    customerRepository.saveAll(customers);
    System.out.println("Customers loaded = " + customerRepository.count());
  }

  private void loadVendors() {
    final Vendor vendor1 = new Vendor();
    vendor1.setName("George Lucas");
    final Vendor vendor2 = new Vendor();
    vendor2.setName("Aldin Marx");
    final Vendor vendor3 = new Vendor();
    vendor3.setName("Natasha Wang");
    vendorRepository.saveAll(Arrays.asList(vendor1, vendor2, vendor3));
    System.out.println("Vendors loaded = " + vendorRepository.count());
  }
}
