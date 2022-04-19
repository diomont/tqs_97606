from flask import Flask, render_template, request
import requests


app = Flask(__name__)

BASE_URI = "http://localhost:8080/api/v1/"

@app.route("/")
def index():
    region_name = request.args.get("regionname", "Global")
    region_iso = request.args.get("region", "global")
    date = request.args.get("date", None)

    regions = requests.get(BASE_URI + "regions").json()

    params = {}
    if region_name != "Global" and region_iso != "global":
        params["iso"] = region_iso
    if date:
        params["date"] = date

    data = requests.get(BASE_URI + "incidence", params=params).json()

    return render_template("index.html", regions=regions, data=data, region_name = region_name)