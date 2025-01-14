package employee_management_app.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import employee_management_app.model.enums.UserRole;
import employee_management_app.model.enums.UserStatus;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_users", indexes = {
		@Index(name = "idx_user_username", columnList = "user_name")
})
@Entity
@EqualsAndHashCode(of = "id")
public class AppUser implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "username", nullable = false, unique = true)
	private String username;
	
	@Column(name = "password", nullable = false)
	@JsonIgnore
	private String password;
	
	@Column(name = "failed_attempt")
	private Integer failedAttempt;
	
	@Builder.Default
	@Column(name = "account_non_expired")
    private Boolean accountNonExpired = true;
    
	@Builder.Default
    @Column(name = "account_non_locked")
    private Boolean accountNonLocked = true;
    
	@Builder.Default
    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired = true;

	@Column(name = "lock_time")
	private LocalDateTime lockTime;
	
	@Column(name = "last_login")
	private LocalDateTime lastLogin;
	
	@Column(name = "password_reset_token")
	private String passwordResetToken;
	
	@Column(name = "reset_token_expiry")
	private LocalDateTime resetTokenExpiry;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;
	
	@CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "status", nullable = false)
    private UserStatus status = UserStatus.ACTIVE;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
    		name = "user_roles",
    		joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    @Builder.Default
    private Set<String> roles = new HashSet<>();
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        return authorities;
    }
    
    public void addRole(UserRole role) {
    	this.roles.add(role.name());
    }

}
