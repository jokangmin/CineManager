package cineManager.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor 
@Getter 
@Setter

public class MemberDTO {
	
	@NonNull 
	private String name; // 이름
	@NonNull
	private String id; // id
	@NonNull
	private String pwd; // password
	@NonNull
	private String phone; // 전화번호
	
	@Override
	public String toString() {
		return name + "\t" + id + "\t" + pwd + "\t" + phone;
	}
}
