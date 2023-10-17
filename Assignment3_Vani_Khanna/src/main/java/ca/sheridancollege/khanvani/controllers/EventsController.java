package ca.sheridancollege.khanvani.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.khanvani.beans.Ticket;
import ca.sheridancollege.khanvani.beans.User;
import ca.sheridancollege.khanvani.repositories.SecurityRepository;
import ca.sheridancollege.khanvani.repositories.TicketRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor

public class EventsController {

	private SecurityRepository secRepo;

	private TicketRepository ticketRepo;

	@GetMapping("/")
	public String goWelcome(Model model) {
		ArrayList<Ticket> tickets = ticketRepo.getTickets();
		model.addAttribute("meow", tickets);
		return "welcome.html";
	}

	@GetMapping("/home")
	public String goHome(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String guestName = authentication.getName();

		ArrayList<Ticket> tickets = ticketRepo.getTickets();
		model.addAttribute("meow", tickets);

		double ticketPrice = 0.0;

		ArrayList<Ticket> guestTickets = ticketRepo.getTicketsbyUsername(guestName);
		model.addAttribute("guestTickets", guestTickets);

		for (Ticket ticket : guestTickets) {
			ticketPrice += ticket.getPrice();
		}
		model.addAttribute("ticketPrice", ticketPrice);
		double sTotal = ticketPrice;
		double taxesforTicket = sTotal * 0.13;
		double totalPrice = sTotal + taxesforTicket;

		String formattedsTotal = String.format("%.2f", sTotal);
		String formattedtaxesforTicket = String.format("%.2f", taxesforTicket);
		String formattedtotalPrice = String.format("%.2f", totalPrice);

		model.addAttribute("sTotal", formattedsTotal);
		model.addAttribute("taxesforTicket", formattedtaxesforTicket);
		model.addAttribute("totalPrice", formattedtotalPrice);

		return "home.html";
	}

	@GetMapping("/login") // it will load the login page
	public String goLogin() {
		return "Login.html";
	}

	@GetMapping("/add")
	public String goAddPage(Model model) {
		model.addAttribute("ticket", new Ticket());

		List<User> guestUsers = secRepo.getAllGuestUsers();

		model.addAttribute("users", guestUsers);
		return "addTicket.html";
	}

	@PostMapping("/add")
	public String goAdd(@ModelAttribute Ticket ticket, Model model) {

		System.out.println(ticket);
		ticketRepo.addTicket(ticket);

		return "redirect:/home";
	}

	@GetMapping("/edit/{id}")
	public String GoeditPage(Model model, @PathVariable int id) {
		Ticket t = ticketRepo.getTicketById(id);
		model.addAttribute("ticket", t);
		return "editTicket.html";
	}

	@PostMapping("/edit")
	public String processEditPage(@ModelAttribute Ticket ticket, Model model) {
		System.out.println("Edit Ticket: " + ticket);

		ticketRepo.editTicket(ticket);
		// System.out.println("Date : " + ticket.getEventDate());
		return "redirect:/home";
	}

	@GetMapping("/delete/{id}")
	public String deleteTicket(@PathVariable int id) {
		ticketRepo.deleteTicket(id);
		return "redirect:/home";
	}

	@GetMapping("/register")
	public String goRegister() {
		return "register.html";
	}

	@PostMapping("/register")
	public String processRegister(@RequestParam String username, @RequestParam String password) {
		secRepo.createUser(username, password);
		User user = secRepo.getUsersByUserName(username);

		secRepo.createRole(user.getUserId(), 2);

		return "redirect:/login";
	}


	@GetMapping("/addTicket")
	public String showAddTicketPage(Model model) {
		// Retrieve the list of registered guest users
		List<User> guestUsers = secRepo.getAllGuestUsers();

		// Add the list of guest users to the model to be used in the Thymeleaf template
		model.addAttribute("users", guestUsers);

		// Return the name of the Thymeleaf template for the add ticket page

		model.addAttribute("ticket", new Ticket());
		return "addTicket";
	}

}
