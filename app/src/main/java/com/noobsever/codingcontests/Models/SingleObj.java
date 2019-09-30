package com.noobsever.codingcontests.Models;

public class SingleObj
{


    private Integer duration;
    private String end;
    private Integer Key_Id;
    private String event;
    private String href;
    private Integer id;
    private SingleObj.Res resource;
    private String start;


    public Integer getKey_Id() {
        return Key_Id;
    }

    public void setKey_Id(Integer key_Id) {
        Key_Id = key_Id;
    }

    public SingleObj(Integer duration, String end, Integer key_Id, String event, String href, Integer id, Res resource, String start) {
        this.duration = duration;
        this.end = end;
        Key_Id = key_Id;
        this.event = event;
        this.href = href;
        this.id = id;
        this.resource = resource;
        this.start = start;
    }

    public SingleObj() {
    }

    public SingleObj(Integer duration, String end, String event, String href, Integer id, SingleObj.Res resource, String start) {
        super();
        this.duration = duration;
        this.end = end;
        this.event = event;
        this.href = href;
        this.id = id;
        this.resource = resource;
        this.start = start;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SingleObj.Res getResource() {
        return resource;
    }

    public void setResource(SingleObj.Res resource) {
        this.resource = resource;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }



    public static class Res
    {

        private Integer id;
        private String name;
        private final static long serialVersionUID = -3652127644136368607L;


        public Res() {
        }

        public Res(Integer id, String name) {
            super();
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }


}
