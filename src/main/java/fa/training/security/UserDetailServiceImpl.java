package fa.training.security;

import fa.training.models.entities.Content;
import fa.training.models.entities.Member;
import fa.training.services.ContentService;
import fa.training.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    MemberService memberService;
    @Autowired
    ContentService contentService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberService.findMemberByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException("Member not found with email: " + email);
        }
        List<GrantedAuthority> grantList = findGrantedAuthoritiesByMember(member);

        return new User(member.getEmail(),
                        member.getPassword(),
                        grantList);
    }

    public void updateAuthorize(Authentication authentication) {
        loadUserByUsername(authentication.getName());
        authentication.setAuthenticated(false);
        Member member = memberService.findMemberByEmail(authentication.getName());
        UsernamePasswordAuthenticationToken newAuth =
                new UsernamePasswordAuthenticationToken(
                        authentication.getPrincipal(),
                        authentication.getCredentials(),
                        findGrantedAuthoritiesByMember(member));
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    public List<GrantedAuthority> findGrantedAuthoritiesByMember(Member member) {
        List<GrantedAuthority> grantList = new ArrayList<>();
        List<Content> contentList = contentService.findAllByMemberIs(member);
        if(contentList.size() != 0){
            for (Content content : contentList) {
                String contentId = String.valueOf(content.getContentId());
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_AUTHOR" + contentId);
                grantList.add(authority);
            }
        }
        return grantList;
    }
}
