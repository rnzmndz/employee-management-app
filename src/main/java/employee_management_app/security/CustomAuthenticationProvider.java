package employee_management_app.security;

//
//@Component
//@Primary
public class CustomAuthenticationProvider /* implements AuthenticationProvider */ {
	
//	@Autowired
//	private UserRepository userRepository;
//	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	
//	@Autowired
//	private UserSecurityService userSecurityService;
//
//	@Override
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		String username = authentication.getName();
//		String password = authentication.getCredentials().toString();
//		
//		AppUser user = userRepository.findByUserName(username)
//				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
//		
//		if (!user.isAccountNonLocked()) {
//				if (userSecurityService.unlockWhenTimeExpired(user)) {
//					return authenticateUser(user, password);
//			}
//		} else {
//			throw new LockedException("Account is locked");
//		}
//		return authenticateUser(user, password);
//	}
//
//	private Authentication authenticateUser(AppUser user, String password) {
//        if (passwordEncoder.matches(password, user.getPassword())) {
//            userSecurityService.resetFailedAttempts(user);
//            userSecurityService.updateLastLogin(user);
//           
//            return new UsernamePasswordAuthenticationToken(
//                user.getUserName(), 
//                password,
//                user.getAuthorities()
//            );
//        } else {
//            userSecurityService.incrementFailedAttempts(user);
//            throw new BadCredentialsException("Invalid password");
//        }
//    }
//	
//	@Override
//	public boolean supports(Class<?> authentication) {
//		return authentication.equals(UsernamePasswordAuthenticationToken.class);
//	}

}
