package com.zonions.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements Serializable {


  private static final long serialVersionUID = 65981149772133526L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USER_ID")
  private Long id;

  @Column(name = "PROVIDER_USER_ID")
  private String providerUserId;

  private String email;

  @Column(name = "enabled", columnDefinition = "BIT", length = 1)
  private boolean enabled;

  @Column(name = "DISPLAY_NAME")
  private String displayName;

  @Column(name = "created_date", nullable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  protected Date createdDate;

  @Temporal(TemporalType.TIMESTAMP)
  protected Date modifiedDate;

  private String password;

  private String provider;

  @ManyToMany
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  @Fetch(FetchMode.JOIN)
  private Set<Role> roles = new HashSet<>();
}
