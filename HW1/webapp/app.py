from tracemalloc import start
from flask import Flask, render_template, request
import requests


app = Flask(__name__)

BASE_URI = "http://localhost:8080/api/v1/"

@app.route("/")
def index():
    region_name = request.args.get("regionname", "Global")
    region_iso = request.args.get("region", "global")
    date = request.args.get("date", None)
    start_date = request.args.get("start", None)
    end_date = request.args.get("end", None)


    regions = requests.get(BASE_URI + "regions").json()

    params = {}
    if region_name != "Global" and region_iso != "global":
        params["iso"] = region_iso
    if date:
        params["date"] = date
    elif start_date and end_date:
        params["start"] = start_date
        params["end"] = end_date

    
    data = requests.get(BASE_URI + "incidence", params=params)#.json()
    print(data)
    data = data.json()

    return render_template("index.html", regions=regions, data=data, region_name = region_name, date=date, start_date=start_date, end_date=end_date)