package Data;

import java.io.Serializable;

public class BasicInfo implements Serializable {
    protected String adrress=null;
    protected String nick_name=null;
    protected String id;
    protected String password=null;
    protected String mail_address=null;
    protected String[] question=null;
    protected String[] solution=null;
    protected Integer age;
    protected String signature=null;
    protected String job=null;
    protected ClientStatus status=null;
    boolean sex=false;
    public static class BasicInfoBuilder {
        protected String address=null;
        protected String id;
        protected String nick_name = null;
        protected String password = null;
        protected String mail_address = null;
        protected String[] question = null;
        protected String[] solution = null;
        protected Integer age;
        protected String signature = null;
        protected String job = null;
        boolean sex=false;
        protected ClientStatus status = null;

        public BasicInfoBuilder(String name, String id) {
            this.nick_name=name;
            this.id=id;
        }
        public  BasicInfoBuilder SetSex(boolean se){
            this.sex=sex;
            return this;
        }
        public  BasicInfoBuilder SetMailAdress(String mail_address){
            this.mail_address=mail_address;
            return this;
        }
        public  BasicInfoBuilder SetQuestion(String[] Question){
            this.question=question;
            return this;
        }
        public  BasicInfoBuilder SetSolution(String[] solution){
            this.solution=solution;
            return this;
        }
        public  BasicInfoBuilder SetJob(String job){
            this.job=job;
            return this;
        }
        public  BasicInfoBuilder SetSignatrue(String signature){
            this.signature=signature;
            return this;
        }
        public  BasicInfoBuilder SetAge(Integer age){
            this.age=age;
            return this;
        }
        public  BasicInfoBuilder SetClientStatus(ClientStatus status){
            this.status=status;
            return this;
        }
        public BasicInfo Builder(){
            return new BasicInfo(this);
        }
    }
    private BasicInfo(BasicInfoBuilder build){
        this.age=build.age;
        this.id=build.id;
        this.job=build.job;
        this.mail_address=build.mail_address;
        this.password=build.password;
        this.question=build.question;
        this.signature=build.signature;
        this.solution=build.solution;
        this.status=build.status;
        this.nick_name=build.nick_name;
        this.adrress=build.address;
    }

    public String getAdrress() {
        return adrress;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public Integer getAge() {
        return age;
    }

    public String getId() {
        return id;
    }

    public boolean getSex() {
        return sex;
    }

    public String getJob() {
        return job;
    }

    public String getMail_address() {
        return mail_address;
    }

    public String getPassword() {
        return password;
    }
    public String getSignature() {
        return signature;
    }

    public String getNick_name() {
        return nick_name;
    }

    public String[] getQuestion() {
        return question;
    }

    public String[] getSolution() {
        return solution;
    }
}

