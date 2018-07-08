package dto;

public class RacunDTO {
	
	private Long vlasnikId;
	
	private String brojRacuna;
	
	private Long valutaId;

	public RacunDTO() {
		
	}

	public RacunDTO(Long vlasnikId, String brojRacuna, Long valutaId) {
		super();
		this.vlasnikId = vlasnikId;
		this.brojRacuna = brojRacuna;
		this.valutaId = valutaId;
	}

	public Long getVlasnikId() {
		return vlasnikId;
	}

	public void setVlasnikId(Long vlasnikId) {
		this.vlasnikId = vlasnikId;
	}

	public String getBrojRacuna() {
		return brojRacuna;
	}

	public void setBrojRacuna(String brojRacuna) {
		this.brojRacuna = brojRacuna;
	}

	public Long getValutaId() {
		return valutaId;
	}

	public void setValutaId(Long valutaId) {
		this.valutaId = valutaId;
	}

	@Override
	public String toString() {
		return "RacunDTO [vlasnikId=" + vlasnikId + ", brojRacuna=" + brojRacuna + ", valutaId=" + valutaId + "]";
	}
	
	

}
