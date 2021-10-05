	@RequestMapping("/navermailtest")
	public void navermailtest(HttpServletRequest request, ModelMap mo) throws Exception{
		
		// 메일 관련 정보
        String host = "smtp.naver.com";
        final String username = "XXXXXXX";       //네이버 이메일 주소중 @ naver.com앞주소만 기재합니다.
        final String password = "1234";   //네이버 이메일 비밀번호를 기재합니다.
        int port=465;
        
        // 메일 내용
        String recipient = "XXXXXXX@nate.com";    //메일을 발송할 이메일 주소를 기재해 줍니다.
        String subject = "네이버를 사용한 발송 테스트입니다.";
        String body = "내용 무";
        
        Properties props = System.getProperties();
         
         
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", host);
          
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            String un=username;
            String pw=password;
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(un, pw);
            }
        });
        session.setDebug(true); //for debug
          
        Message mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress("glant89@naver.com"));
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(body);
        Transport.send(mimeMessage);

	}
