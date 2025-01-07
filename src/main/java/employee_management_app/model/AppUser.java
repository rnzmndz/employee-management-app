package employee_management_app.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_users", indexes = {
		@Index(name = "idx_user_username", columnList = "user_name")
})
@Entity
@EqualsAndHashCode(of = "id")
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_name", nullable = false, unique = true)
	private String userName;
	
	@Column(name = "password", nullable = false)
	@JsonIgnore
	private String password;
	
	@Column(name = "failed_attempt")
	private Integer failedAttempt;
	
	@Column(name = "account_non_locked")
	private boolean accountNonLocked = true;
	
	@Column(name = "lock_time")
	private LocalDateTime lockTime;
	
	@Column(name = "last_login")
	private LocalDateTime lastLogin;
	
	@Column(name = "password_reset_token")
	private String passwordResetToken;
	
	@Column(name = "reset_token_expiry")
	private LocalDateTime resetTokenExpiry;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private UserRole role;
	
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
    @Column(name = "status", nullable = false)
    private UserStatus status = UserStatus.ACTIVE;
    
    @ElementCollection
    @CollectionTable(
    		name = "user_permission",
    		joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "permission")
    private Set<String> permissions = new HashSet<>();
    
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        permissions.forEach(permission -> 
            authorities.add(new SimpleGrantedAuthority(permission))
        );
        return authorities;
	}
}
