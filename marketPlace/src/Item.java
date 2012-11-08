	public class Item {

		String name;
		float price;

		public Item(String name, float price) {
			super();
			this.name = name;
			this.price = price;
		}
		public String toString() {
			return name + ": $" + price;
		}
	}