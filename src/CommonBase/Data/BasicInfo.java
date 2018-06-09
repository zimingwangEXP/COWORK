package CommonBase.Data;
import  CommonBase.Data.UserSnapShot;
import java.io.Serializable;
import java.util.Date;

public class BasicInfo implements Serializable {
    protected String adrress=null;
    protected String nick_name=null;
    protected String id;
    protected String password=null;
    protected String mail_address=null;
    protected String question=null;
    protected String solution=null;
    protected Integer age;
    protected String birthday=null;
    protected  String phone_number=null;
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
        protected String question = null;
        protected String birthday=null;
        protected  String phone_number=null;
        protected String solution = null;
        protected Integer age;
        protected String signature = null;
        protected String job = null;
        boolean sex=false;
        protected ClientStatus status = null;

        public BasicInfoBuilder(String name, String id) {
            this.nick_name=name;
            this.id=id;
        }

        public BasicInfoBuilder SetId(String id) {
            this.id = id;
            return this;
        }

        public BasicInfoBuilder SetAddress(String address) {
            this.address = address;
            return this;
        }

        public BasicInfoBuilder SetNick_name(String nick_name) {
            this.nick_name = nick_name;
            return this;
        }

        public BasicInfoBuilder SetPhone_number(String phone_number) {
            this.phone_number = phone_number;
            return this;
        }

        public BasicInfoBuilder SetBirthday(String birthday) {
            this.birthday = birthday;
            return this;
        }

        public  BasicInfoBuilder SetSex(boolean se){
            this.sex=sex;
            return this;
        }
        public  BasicInfoBuilder SetMailAdress(String mail_address){
            this.mail_address=mail_address;
            return this;
        }
        public  BasicInfoBuilder SetQuestion(String Question){
            this.question=question;
            return this;
        }
        public  BasicInfoBuilder SetSolution(String solution){
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

        public BasicInfoBuilder SetPassword(String password) {
            this.password = password;
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
        this.adrress=build.address;
        this.age=build.age;
        this.birthday=build.birthday;
        this.id=build.id;
        this.job=build.job;
        this.mail_address=build.mail_address;
        this.nick_name=build.nick_name;
        this.password=build.password;
        this.phone_number=build.phone_number;
        this.sex=build.sex;
        this.question=build.question;
        this.signature=build.signature;
        this.solution=build.solution;
        this.status=build.status;


    }
    public void Show(){
        System.out.println("用户信息表述:");
        System.out.println("id:"+id+" nick_name:"+nick_name);
        System.out.println("age:"+age+" birthday:"+birthday);
        System.out.println("address:"+adrress+" mail_address:"+mail_address);
        System.out.println("id:"+id+" nick_name:"+nick_name);
        System.out.println("password:"+password+" phone_number:"+phone_number);
        System.out.println("job:"+job+" signature:"+signature);
        System.out.println("question:"+question+" solution:"+solution);
        System.out.println("sex:"+(sex?"FEMALE":"MALE")+" signature:"+signature);
        System.out.println("status:"+status.toString());
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

    public String getQuestion() {
        return question;
    }

    public String getSolution() {
        return solution;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdrress(String adrress) {
        this.adrress = adrress;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setMail_address(String mail_address) {
        this.mail_address = mail_address;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPhone_number() {
        return phone_number;
    }
}

