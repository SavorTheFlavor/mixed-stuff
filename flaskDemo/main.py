from flask import Flask, request, render_template

app = Flask(__name__)


# 当本模块被直接执行时，__name__=__main__
# 否则(被其他模块import)__name__=该模块名

@app.route('/')
@app.route('/<user>')
def index(user=None):
    return render_template("user.html",user=user)

@app.route('/profile/<username>')
def profile(username):
    return "<h1>hey! %s</h1>" % username


@app.route('/post/<int:post_id>')
def show_post(post_id):
    post_id = post_id + 1000
    return "post id is not %s" % post_id

@app.route('/bacon',methods=['GET','POST'])
def bacon():
    if request.method == 'POST':
        return 'You are using POST'
    else:
        return 'You are probably using GET'

@app.route('/profile2/<username>')
def atemplate(username):
    return render_template("profile.html", name=username)

@app.route('/shopping')
def shopping():
    food = ["cheese", "tuna", "beef"]
    return render_template("shopping.html", food=food)

if __name__ == "__main__":
    app.run(debug=True)
